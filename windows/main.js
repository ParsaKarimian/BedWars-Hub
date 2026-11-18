const {app,BrowserWindow,shell,Menu}=require('electron');
const SITE='https://karimal.ir',OK=/^https:\/\/([\w-]+\.)?karimal\.ir(\/|$)/; // change to your domain
app.commandLine.appendSwitch('autoplay-policy','no-user-gesture-required');
function create(){
 const w=new BrowserWindow({width:1280,height:800,minWidth:900,minHeight:600,backgroundColor:'#0a0a14',title:'BedWars Hub',icon:__dirname+'/build/icon.png',webPreferences:{contextIsolation:true,sandbox:true}});
 Menu.setApplicationMenu(null);w.loadURL(SITE+'/home');
 w.webContents.setWindowOpenHandler(({url})=>{if(!OK.test(url)){shell.openExternal(url);return{action:'deny'}}return{action:'allow'}});
 w.webContents.on('will-navigate',(e,url)=>{if(!OK.test(url)){e.preventDefault();shell.openExternal(url)}});
}
app.whenReady().then(create);app.on('window-all-closed',()=>app.quit());
