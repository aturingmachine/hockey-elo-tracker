import { app, BrowserWindow, ipcMain } from "electron";
const SerialPort = require("serialport");
const Readline = require("@serialport/parser-readline");

/**
 * Set `__static` path to static files in production
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-static-assets.html
 */
if (process.env.NODE_ENV !== "development") {
  global.__static = require("path")
    .join(__dirname, "/static")
    .replace(/\\/g, "\\\\");
}

let mainWindow;
let parser;
let oldData;
let port;
let scannerPort;

const winURL =
  process.env.NODE_ENV === "development"
    ? `http://localhost:9080`
    : `file://${__dirname}/index.html`;

function createWindow() {
  const shouldUseFrame = process.env.NODE_ENV === "development" ? true : false;
  /**
   * Initial window options
   */
  mainWindow = new BrowserWindow({
    height: 480,
    useContentSize: true,
    width: 800,
    frame: shouldUseFrame
  });

  mainWindow.loadURL(winURL);

  mainWindow.on("closed", () => {
    mainWindow = null;
  });

  SerialPort.list((err, ports) => {
    if (err) {
      console.log("SerialPort.list err");
      console.log(err);
    }

    console.log(ports);

    scannerPort = ports.find(port => {
      return port.comName === "/dev/ttyUSB0";
    });

    if (!!scannerPort) {
      port = new SerialPort(scannerPort.comName);
      parser = port.pipe(
        new Readline({
          delimiter: "\r\n"
        })
      );
    } else {
      console.log("Unable to mount scanner.");
    }

    // port.on("open", () => {
    //   parser.on("data", x => {
    //     console.log(x);
    //   });
    // });
    // }
  });
}

app.on("ready", createWindow);

app.on("window-all-closed", () => {
  if (!!scannerPort) {
    port.close(() => {
      console.log("Port Closed to Card Reader");
      if (process.platform !== "darwin") {
        app.quit();
      }
    });
  } else {
    if (process.platform !== "darwin") {
      app.quit();
    }
  }
});

app.on("activate", () => {
  if (mainWindow === null) {
    createWindow();
  }
});

const exists = portName =>
  SerialPort.list().then(ports =>
    ports.some(port => port.comName === portName)
  );

ipcMain.on("health-check-send", event => {
  exists("/dev/ttyUSB0").then(res => {
    console.log("Health Check Initiated; Status: ", !!res ? "Healthy" : "Bad");
    event.sender.send("health-check-response", !!res);
  });
});

//here is the logic for reading a sign in event
//the renderer process sends a read-sign-in, then we
//attempt to read from the card reader
ipcMain.on("read-sign-in", (event, arg) => {
  console.log("attempting to read sign in");
  parser.on("data", data => {
    let asdf = JSON.parse(data);
    console.log("PARSED", asdf);
    if (!!asdf.payload && !!asdf.payload.cardCode) {
      console.log("Doing Logic with", asdf.payload.cardCode, oldData);
      if (asdf.payload.cardCode !== oldData) {
        console.log(data);
        console.log("data is good, sending now");
        oldData = asdf.payload.cardCode;
        event.sender.send("sign-in-read", data);
      } else {
        console.log("read same data");
        console.log("OLD", oldData);
        console.log("NEW", asdf.payload.cardCode);
      }
    }
  });

  // setTimeout(() => {
  //   if (process.env.NODE_ENV === "development") {
  //     console.log("detected dev process");
  //     const fakeDevAuth = {
  //       payload: {
  //         cardCode: Math.floor(Math.random() * 10) + 1
  //       }
  //     };
  //     event.sender.send("sign-in-read", JSON.stringify(fakeDevAuth));
  //   }
  // }, 1000);
});

//here we need to clear our card state on a new game
ipcMain.on("new-game", () => {
  console.log("New Game Initiated, State Reset");
  oldData = undefined;
});

/**
 * Auto Updater
 *
 * Uncomment the following code below and install `electron-updater` to
 * support auto updating. Code Signing with a valid certificate is required.
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-electron-builder.html#auto-updating
 */

/*
import { autoUpdater } from 'electron-updater'

autoUpdater.on('update-downloaded', () => {
  autoUpdater.quitAndInstall()
})

app.on('ready', () => {
  if (process.env.NODE_ENV === 'production') autoUpdater.checkForUpdates()
})
 */
