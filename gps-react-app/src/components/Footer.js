import React from "react"
import logo from "../images/ivanti-logo.svg";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebookF, faLinkedinIn, faTwitter, faYoutube  } from '@fortawesome/free-brands-svg-icons'

function Footer() {
    return (
        <footer>
            <div className={"row"}>
                <img src={logo} height={"44px"} alt={"ivanti logo"}/>
                <div className={"socials"}>
                    <a href="https://www.linkedin.com/company/ivanti">
                        <FontAwesomeIcon className="socials-icon" icon={faLinkedinIn} />
                    </a>
                    <a href="https://www.facebook.com/GoIvanti">
                        <FontAwesomeIcon className="socials-icon" icon={faFacebookF} />
                    </a>
                    <a href="https://www.youtube.com/c/Ivanti">
                        <FontAwesomeIcon className="socials-icon" icon={faYoutube} />
                    </a>
                    <a href="https://twitter.com/IvantiBenelux">
                        <FontAwesomeIcon className="socials-icon" icon={faTwitter} />
                    </a>
                </div>
                <p className={"copyright"}>Copyright &copy; 2022 Ivanti. All rights reserved.</p>
            </div>
            <div className={"row"}>
                <a href="https://www.ivanti.com/webinars">Webinars</a>
                <a href="https://www.ivanti.com/company/events">Events</a>
                <a href="https://www.ivanti.com/blog">Blog</a>
            </div>
            <div className={"row"}>
                <a href="https://www.ivanti.com/company/careers">Careers</a>
                <a href="https://www.ivanti.com/company/contacts">Contact Us</a>
                <a href="https://www.ivanti.com/company/legal">Privacy & Legal</a>
            </div>
            <div className={"row"}>
                <a href="https://www.ivanti.com/partners">Partners</a>
                <a href="https://www.ivanti.com/company/press-releases">Press Releases</a>
                <a href="https://www.ivanti.com/resources/downloads">Downloads</a>
            </div>
        </footer>
    )
}

export default Footer