/*import React, {useEffect, useState} from "react";
import axios from 'axios';

function GetApp({appName}) {

    const [product, setProduct] = useState({});
    function get(e) {
        let reader = new FileReader() //this for convert to Base64
        reader.readAsDataURL(e.target.files[0]) //start conversion...
        reader.onload = function (e) { //.. once finished..
            fetch('https://script.google.com/macros/s/AKfycby3Ey1lmmyX9CAsRlanTAU4FveEyfKqnjrYQPTaaBHUEN6Z3OrF/exec', //your AppsScript URL
                {method: "GET", body: JSON.stringify(dataSend)}) //send to Api
                .then(res => res.json()).then((a) => {
                changeIcon(a.url);

            }).catch((e) => {
            })
        }
    }


    return(
        <div className="container">

        </div>
    );
}

export default GetApp;*/