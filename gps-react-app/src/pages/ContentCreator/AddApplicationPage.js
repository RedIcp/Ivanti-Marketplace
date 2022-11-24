import React, {useState} from "react";
import "../../styles/ContentCreator/AddAndUpdateApplicationPage.css";
import axios from 'axios';
import ReactDOM from "react-dom";
import {useParams, useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCirclePlus, faPen} from "@fortawesome/free-solid-svg-icons";
import useAuth from "../../hooks/useAuth";
import Tutorial from "../../components/Tutorial";
import { BsQuestionCircle } from 'react-icons/bs'

function AddApplicationPage() {

    const { auth } = useAuth();

    let navigate = useNavigate();

    // google api requests
    function SaveArchiveIcon(e) {
        let file = e.target.files[0] //the file
        let reader = new FileReader() //this for convert to Base64
        reader.readAsDataURL(e.target.files[0]) //start conversion...
        reader.onload = function (e) { //.. once finished..
            let rawLog = reader.result.split(',')[1]; //extract only the file data part

            setLoadingIcon();

            let dataSend = {
                dataReq: {data: rawLog, name: file.name, type: file.type},
                fname: "uploadFilesToGoogleDrive"
            }; //preapre info to send to API
            fetch('https://script.google.com/macros/s/AKfycby3Ey1lmmyX9CAsRlanTAU4FveEyfKqnjrYQPTaaBHUEN6Z3OrF/exec', //your AppsScript URL
                {method: "POST", body: JSON.stringify(dataSend)}) //send to Api
                .then(res => res.json()).then((a) => {
                changeIcon(a.url);
                setLoadingIcon("");
            }).catch((e) => {
                setLoadingIcon("Something went wrong please try again later.\n" + e);
            })
        }
    }
    function SaveArchiveImage(e) {
        let file = e.target.files[0] //the file
        let reader = new FileReader() //this for convert to Base64
        reader.readAsDataURL(e.target.files[0]) //start conversion...
        reader.onload = function (e) { //.. once finished..
            let rawLog = reader.result.split(',')[1]; //extract only the file data part

            setLoadingImage("Loading...");

            let dataSend = {
                dataReq: {data: rawLog, name: file.name, type: file.type},
                fname: "uploadFilesToGoogleDrive"
            }; //preapre info to send to API
            fetch('https://script.google.com/macros/s/AKfycby3Ey1lmmyX9CAsRlanTAU4FveEyfKqnjrYQPTaaBHUEN6Z3OrF/exec', //your AppsScript URL
                {method: "POST", body: JSON.stringify(dataSend)}) //send to Api
                .then(res => res.json()).then((a) => {
                AddImage(a.url);
                setLoadingImage("");
            }).catch((e) => {
                setLoadingImage("Something went wrong please try again later.\n" + e);
            })
        }
    }
    function SaveArchiveApp(e){
        let file = e.target.files[0] //the file
        let reader = new FileReader() //this for convert to Base64
        reader.readAsDataURL(e.target.files[0]) //start conversion...
        reader.onload = function (e) { //.. once finished..
            let rawLog = reader.result.split(',')[1]; //extract only the file data part

            setLoadingApp("Loading...");

            let dataSend = {
                dataReq: {data: rawLog, name: file.name, type: file.type},
                fname: "uploadFilesToGoogleDrive"
            }; //preapre info to send to API
             fetch('https://script.google.com/macros/s/AKfycby3Ey1lmmyX9CAsRlanTAU4FveEyfKqnjrYQPTaaBHUEN6Z3OrF/exec', //your AppsScript URL
                {method: "POST", body: JSON.stringify(dataSend)}) //send to Api
                .then(res => res.json()).then((a) => {
                    changeApp(a.url);
                    setLoadingApp("");
                }).catch((e) => {
                    setLoadingApp("Something went wrong please try again later.\n" + e);
                })
        }
    }

    //Variables
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [screenshots, setScreenshots] = useState([])
    const [icon, setIcon] = useState("");
    const [appLocation, setAppLocation] = useState("");
    const [creatorID, setCreatorID] = useState('');

    //Loading screens
    const [loadingImage, setLoadingImage] = useState("");
    const [loadingIcon, setLoadingIcon] = useState("");
    const [loadingApp, setLoadingApp] = useState("");
    const [loadingSavingApp, setLoadingSavingApp] = useState("");

    //Save changes
    function changeIcon(url) {

        let includeID = url.replace("file/d/", "uc?export=view?&id=");
        let ChangeView = includeID.replace("/view?usp=drivesdk", "");

        setIcon(ChangeView);
    }
    const changeName = (e) => {
        setName(e.target.value);
    };
    const changeDescription = (e) => {
        setDescription(e.target.value);
    }
    function AddImage(url) {
        let includeID = url.replace("file/d/", "uc?export=view?&id=");
        let ChangeView = includeID.replace("/view?usp=drivesdk", "");

        if (screenshots === null) {
            setScreenshots(ChangeView);
        } else if (screenshots.length < 10) {
            screenshots.push(ChangeView);
            setScreenshots(screenshots);
        }
        //ReactDOM.render( <div id="images"><LoadImages/></div>, document.getElementById('images'));
        setScreenshots(screenshots);
    }
    const RemoveImage = (e) => {
        for (let i = 0; i < screenshots.length; i++) {
            if (screenshots[i] === e.target.value) {
                screenshots.splice(i, 1);
                ReactDOM.render( <LoadImages/>, document.getElementById('images'));
                return;
            }
        }
    }
    function changeApp(url) {

        let includeID = url.replace("file/d/", "uc?export=view?&id=");
        let ChangeView = includeID.replace("/view?usp=drivesdk", "");

        setAppLocation(ChangeView);
    }

    //Display images
    function LoadImages() {
        return (
            <div id="images" className={"screenshots-list"}>
                {screenshots.map(image => (
                    <div className={"screenshot"}>
                        <img src={image} alt={"application screenshot"}/>
                        <button className={"delete-screenshot-button"} type="button" value={image} onClick={RemoveImage}>-</button>
                    </div>
                ))}
            </div>
        );
    }

    //Save app
    const config = {
        headers: { Authorization: `Bearer ${auth?.accessToken}` }
    };
    const SendRequest = () =>{

        let data = {
            'name': name,
            'description': description,
            'screenshots': screenshots,
            'icon':icon,
            'applicationLocation': appLocation,
            'creatorID': auth?.decoded?.sub
        };

        axios.post(`http://localhost:8080/application`,data,config)
            .then(function (){})
            .catch(function (){});
    }
    const CheckInput = () =>{

        let result = "";

        if(icon === ""){
            result += "Please add a icon. \n";
        }
        if(description === ""){
            result +="Please add a description. \n";
        }
        if(name === "" ){
            result += "Please add a title. \n";
        }
        if(screenshots === null || screenshots.length === 0) {
            result += "Please add at least 1 image. \n";
        }
        if(appLocation === "") {
            result += "Please make sure to include the url to the application. \n";
        }
        return result;
    }
    const SaveApp = () =>{
        let checkInput = CheckInput();

        if(checkInput !== ""){
            alert(checkInput);}
        else{
            SendRequest();
            alert("App Created!")
            navigate('/creator/my-apps')
        }
    }

    // tutorial
    const [showTutorial, setShowTutorial] = useState(false) 
    const closeTutorial = () => {
        setShowTutorial(false)
    }
    const tutorialContent = {
        'title': 'Add Application Page',
        'header': 'Upload a new application',
        'body': 'This is where you can upload a new application. All you need to do is enter the application name, add a description, upload at least one screenshot and finally upload the package itself, as a zip file. When you are finished, press done and the application is uploaded to the marketplace.'
    }

    return (
        <div className="container">
            <Tutorial visible={showTutorial} closeTutorial={closeTutorial} content={tutorialContent}/>
            <span id={"error"}/>
            <div className={"app-controls"}>
                <img className={"icon"} src={icon} alt={"application icon"}/>
                <h1>{name}</h1>
                <button onClick={() => setShowTutorial(true)} className='btn-open-tutorial'><BsQuestionCircle /></button>
                <button className={"done-button"} onClick={SaveApp}>Done</button>
            </div>
            <hr/>
            <div className={"app-icon"}>
                <h2>Icon:</h2>
                <div className={"icon-controls"}>
                    <img src={icon} alt={"application icon"}/>
                    <label htmlFor="editIconButton"><FontAwesomeIcon className="edit-icon" icon={faPen} /></label>
                    <input id="editIconButton" type="file" accept="image/jpeg, image/png"
                           onChange={(e) => SaveArchiveIcon(e)}/>
                    <p className={"loading-icon"}>{loadingIcon}</p>
                </div>
            </div>
            <hr/>
            <div className={"app-name"}>
                <h2>Name:</h2>
                <input type="text" value={name} placeholder={"Type here..."} onChange={changeName}/>
            </div>
            <hr/>
            <div className="app-screenshots">
                <div className={"screenshots-header"}>
                    <h2>Screenshots:</h2>
                    <label htmlFor="addScreenshot"><FontAwesomeIcon className="add-screenshot-icon" icon={faCirclePlus} /></label>
                    <input id={"addScreenshot"} type="file" accept="image/jpeg, image/png" onChange={(e) => SaveArchiveImage(e)}/>
                    <p className={"loading-screenshot"}>{loadingImage}</p>
                </div>
                <LoadImages/>
            </div>
            <hr/>
            <div className={"app-description"}>
                <h2>Description:</h2>
                <textarea className={"description-field"} value={description} onChange={changeDescription}/>
            </div>
            <hr/>
            <div className={"app-file"}>
                <h2>File:</h2>
                <label htmlFor="addFile"><FontAwesomeIcon className="add-file-icon" icon={faCirclePlus} /></label>
                <input id={"addFile"} type="file" accept="application/zip" onChange={(e) => SaveArchiveApp(e)}/>
                <p className={"AppLoading"}> {loadingApp} </p>
            </div>
        </div>
    );
}

export default AddApplicationPage;
