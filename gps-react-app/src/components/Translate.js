import React, {useEffect} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faGlobe} from "@fortawesome/free-solid-svg-icons";


function Translate() {
    function showContentTranslate() {
        document.getElementById("myDropdownTranslate").classList.toggle("show");
    }

    window.onclick = function(e) {
        if (!e.target.matches('.dropdown-button-translate')) {
            let myDropdownTranslate = document.getElementById("myDropdownTranslate");

            if (myDropdownTranslate != null) {
                if (myDropdownTranslate.classList.contains('show')) {
                    myDropdownTranslate.classList.remove('show');
                }
            }
        }
    }

    const googleTranslateElementInit = () => {
        new window.google.translate.TranslateElement(
            {
                pageLanguage: "en",
                layout: window.google.translate.TranslateElement.InlineLayout.SIMPLE,
                autoDisplay: false
            },
            "google_translate_element"
        );
    };

    useEffect(() => {
        let addScript = document.createElement("script");
        addScript.setAttribute(
            "src",
            "//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"
        );
        document.body.appendChild(addScript);
        window.googleTranslateElementInit = googleTranslateElementInit;
    }, []);

    return (
        <div className="NavDropdownTranslate">
            <button className="dropdown-button-translate" onClick={showContentTranslate}>Translate<FontAwesomeIcon className="NavIcon" icon={faGlobe} /></button>
            <div className="dropdown-content" id="myDropdownTranslate">
                <div id="google_translate_element"/>
            </div>
        </div>
    )
}

export default Translate;