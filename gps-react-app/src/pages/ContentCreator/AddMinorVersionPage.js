import React, {useEffect, useState} from "react";
import axios from "axios";
import {useParams,useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPen} from "@fortawesome/free-solid-svg-icons";
import useAuth from "../../hooks/useAuth";

function AddMinorVersionPage() {

    const { auth } = useAuth()

    let navigate = useNavigate();
    useEffect(() => {
        GetProductByName();
    }, []);

    let params = useParams();

    const config = {
        headers: { Authorization: `Bearer ${auth?.accessToken}` }
    };

    const GetProductByName = () => {
        axios.get(`http://localhost:8080/version/${params.name}`,config)
            .then(res => {
                setVersion(res.data);
            }).catch(e => {
            setVersion(null);
        })
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

    const [version, setVersion] = useState();

    const [loadingApp, setLoadingApp] = useState("");
    const [app, setApp] = useState("");

    function changeApp(url) {

        let includeID = url.replace("file/d/", "uc?export=view?&id=");
        let ChangeView = includeID.replace("/view?usp=drivesdk", "");

        setApp(ChangeView);
    };

    const SendRequest = () =>{
        axios.post(`http://localhost:8080/version/minor`,
            {
                'appName': params.name,
                'number': parseFloat(version + 0.1).toFixed(2),
                'appLocation': app,
            },config)
            .then(function () {})
            .catch(function (){});
    }
    const CheckInput = () =>{

        let result = "";
        if(app == "") {
            result += "Please make sure to include the url to the application. \n";
        }
        return result;
    }
    const SaveApp = async () => {
        let checkInput = CheckInput();

        if (checkInput != "") {
            alert(checkInput);
        } else {
            SendRequest();
            await alert("new version has been added.")
            let path = `/app/` + params.name;
            navigate(path);
        }
    }


    return (
        <div className="container">
            <p>Latest version: {parseFloat(version).toFixed(1)} </p>
            <p>New version number: {parseFloat(version + 0.1).toFixed(1)} </p>
            <br/>
            <label htmlFor="addMinorVersion"><FontAwesomeIcon className="edit-icon" icon={faPen}/>select file</label>
            <input id={"addMinorVersion"} type="file" accept="application/zip" onChange={(e) => SaveArchiveApp(e)}/>
            <p>{loadingApp}</p>
            <button className={"SaveButton"} onClick={SaveApp}>Save</button>
        </div>
    );
}

export default AddMinorVersionPage;