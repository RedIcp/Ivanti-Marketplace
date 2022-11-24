import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import "../../styles/ContentCreator/AddAndUpdateApplicationPage.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCirclePlus, faPen} from '@fortawesome/free-solid-svg-icons'
import ReactDOM from "react-dom";
import useAuth from "../../hooks/useAuth";

function UpdateApplicationPage() {

    const { auth } = useAuth()

    let navigate = useNavigate();

    function SaveArchiveIcon(e) {
        let file = e.target.files[0] //the file
        let reader = new FileReader() //this for convert to Base64
        reader.readAsDataURL(e.target.files[0]) //start conversion...
        reader.onload = function () { //.. once finished..
            let rawLog = reader.result.split(',')[1]; //extract only the file data part

            setLoadingIcon("Loading...");

            let dataSend = {
                dataReq: {data: rawLog, name: file.name, type: file.type},
                fname: "uploadFilesToGoogleDrive"
            }; //prepare info to send to API
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
        reader.onload = function () { //.. once finished..
            let rawLog = reader.result.split(',')[1]; //extract only the file data part

            setLoadingImage("Loading...");

            let dataSend = {
                dataReq: {data: rawLog, name: file.name, type: file.type},
                fname: "uploadFilesToGoogleDrive"
            }; //prepare info to send to API
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

    useEffect(() => {
            GetProductByName();
        }, []);

    const params = useParams();

    const GetProductByName = () => {
            axios.get(`http://localhost:8080/application/` + params.name)
                .then(res => {
                    setIcon(res.data.icon);
                    setName(res.data.name);
                    setDescription(res.data.description);
                    setArrayImages(res.data.screenshots);
                }).catch(() => {
            })
    }

    //App info
    const [icon, setIcon] = useState("");
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [arrayImages, setArrayImages] = useState([])

    //Loading screens
    const [loadingImage, setLoadingImage] = useState("");
    const [loadingIcon, setLoadingIcon] = useState("");

    //Change app info
    function changeIcon(url) {
        let includeID = url.replace("file/d/", "uc?export=view?&id=");
        let ChangeView = includeID.replace("/view?usp=drivesdk", "");
        setIcon(ChangeView);
    }
    const changeDescription = (e) => {
        setDescription(e.target.value);
    }
    function AddImage(url) {
        let includeID = url.replace("file/d/", "uc?export=view?&id=");
        let ChangeView = includeID.replace("/view?usp=drivesdk", "");

        if (arrayImages === null) {
            setArrayImages(ChangeView);
        } else if (arrayImages.length < 10) {
            arrayImages.push(ChangeView);
            setArrayImages(arrayImages);
        }
        
        setArrayImages(arrayImages);
    }
    const RemoveImage = (e) => {
        for (let i = 0; i < arrayImages.length; i++) {
            if (arrayImages[i] === e.target.value) {
                arrayImages.splice(i, 1);
                ReactDOM.render( <LoadImages/>, document.getElementById('images'));
                return;
            }
        }
    }

    //Save changes

    const config = {
        headers: { Authorization: `Bearer ${auth?.accessToken}` }
    };
    const SaveChanges = () => {
        let data = {
            'username': name,
            'description': description,
            'screenshots': arrayImages,
            'icon': icon
        }
        axios.put(`http://localhost:8080/application`, data, config)
            .then(function(){

            })
            .catch(function (){})
    }
    const SaveApp = async () => {
        let checkInput = CheckInput();

        if (checkInput !== "") {
            alert(checkInput);
        } else {
            SaveChanges();
            await alert('app has been updated')
            let path = `/creator/app/` + params.name;
            navigate(path);
        }
    }
    const CheckInput = () => {

        let result = "";

        if (icon === "") {
            result += "Please add a icon. \n";
        }
        
        if (description === "") {
            result += "Please add a description. \n";
        }
        
        if (arrayImages !== null) {
            if (arrayImages.length > 10 || arrayImages.length === 0) {
                result += "Please add at least 1 image. \n";
            }
        } else {
            result += "Please add at least 1 image. \n";
        }
        
        return result;
    }

    //Display images
    function LoadImages() {
        return (
            <div id="images" className={"screenshots-list"}>
                {arrayImages.map(image => (
                    <div className={"screenshot"}>
                        <img src={image} alt={"application screenshot"}/>
                        <button className={"delete-screenshot-button"} type="button" value={image} onClick={RemoveImage}>-</button>
                    </div>
                ))}
            </div>
        );
    }

    if (name != null){
        return(
            <div className="container">
                <div className={"app-controls"}>
                    <img className={"icon"} src={icon} alt={"application icon"}/>
                    <h1>{name}</h1>
                    <button className={"preview-button"}>Preview</button>
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
                    <h2>Name:{name} </h2>
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
            </div>
        );
    } else {
        return(
            <div className="container">
                <p>Not found!</p>
            </div>
        );
    }
}

export default UpdateApplicationPage;