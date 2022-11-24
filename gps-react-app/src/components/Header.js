import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import useAuth from '../hooks/useAuth'
import logo from "../images/ivanti-marketplace-logo.png";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGlobe, faCaretDown } from '@fortawesome/free-solid-svg-icons'
import Translate from './Translate';
import Notification from "../pages/Notification/Notification";
function Navbar() {

    const { auth } = useAuth();
    const [isCreator, setIsCreator] = useState(false)

    const checkRoleStatus = () => {
        auth?.roles?.map((role) => {
            if (role === "Creator") {
                setIsCreator(true)               
            }
        })
    }

    useEffect(() => {
        checkRoleStatus()
    }, [auth])

    function showContent() {
        document.getElementById("myDropdown").classList.toggle("show");
    }

    window.onclick = function(e) {
        if (!e.target.matches('.dropdown-button')) {
            let myDropdown = document.getElementById("myDropdown");

            if (myDropdown !== null) {
                if (myDropdown.classList.contains('show')) {
                    myDropdown.classList.remove('show');
                }
            }
        }
    }

  return (
    <>

        <div className='Nav'>
            <Link className='NavLogo' to={'/'}>
                <img src={logo} height={'38px'} alt={"ivanti marketplace logo"} />
            </Link>
            <Link className='NavLink' to={'/all-apps'}>Apps</Link>
            
           
            { isCreator ? (
                <>
                    {auth?.decoded && <Link className='NavLink' to={'/creator/analytics'}>Analytics</Link>}
                </>
            ) : (null)}



            <Translate />
            {auth?.decoded &&
            
                <div className='NavDropdown'>
                  
                    <button className='dropdown-button' onClick={showContent}>{auth?.decoded.sub}<FontAwesomeIcon className='NavIcon' icon={faCaretDown} /></button>
                    <div className='dropdown-content'  id="myDropdown">
                        { isCreator ? (
                            <>
                                <Link className='NavLink' to={'/creator/my-apps'}>My Apps</Link>
                            </>
                        ) : (null)}
                        <hr />
                        <Link className='NavLink' to={'/logout'}>Log Out</Link>
                        <Notification />
                    </div>
                </ div>
            }
            {!auth?.decoded && <Link className='NavLinkLogin' to={'/login'}>Login</Link>}
        </div>
    </>
  )
}

export default Navbar