import React, { useEffect, useState } from 'react'
import axios from 'axios';
import '../../../styles/ContentCreator/MyAppsPage/MyAppsPage.css';
import { Link } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCirclePlus } from '@fortawesome/free-solid-svg-icons'
import loading from "../../../images/loading.gif";
import useAuth from '../../../hooks/useAuth';
import BasicApplication from '../../../components/BasicApplication';
import Tutorial from '../../../components/Tutorial';
import { BsQuestionCircle } from 'react-icons/bs'

const MyAppsPage = () => {

    const { auth } = useAuth();
    const authorisation = {
        headers: { Authorization: 'Bearer ' + auth?.accessToken }
    }
    const [applications, setApplications] = useState([]);

    const getApplicationsByCreator = () => {
        const URL = `http://localhost:8080/application/creator/${auth?.decoded?.sub}`

        axios.get(URL, authorisation)
        .then(res => {
            setApplications(res.data.myApplications)
        })
        .catch(err => {
            console.log(err)
        })
    }

    useEffect(() => {
        getApplicationsByCreator()
    }, [])

    const [showTutorial, setShowTutorial] = useState(false) 
    const closeTutorial = () => {
        setShowTutorial(false)
    }
    const tutorialContent = {
        'title': 'My Applications Page',
        'header': 'Manage your applications',
        'body': 'On this page you will find an overview of all applications you have added. If you want to update or discontinue an application you can click on it to go to the application page, where you will find the controls. If you want to upload  a new application you can click the plus button.'
    }

    return (
        <>
            <div className='my-apps'>
            <Tutorial visible={showTutorial} closeTutorial={closeTutorial} content={tutorialContent}/>
                <div className='my-apps-controls' style={{justifyContent: "normal"}}>
                    <h1 className='title'>My Apps</h1>
                    <Link to='/creator/add-app'><FontAwesomeIcon className='add-icon' icon={faCirclePlus}/></Link>
                    <button onClick={() => setShowTutorial(true)} className='btn-open-tutorial'><BsQuestionCircle /></button>
                    
                </div>
                <hr/>

                {applications.length !== 0 ? (
                    <div className='my-apps-list'>
                        {applications?.map((app) => (
                            <BasicApplication key={app.name} name={app.name} icon={app.icon}/>
                        ))}
                    </div>
                ) : (
                    <img className={"loading-apps"} src={loading}/>
                )}
            </div>
        </>
    )
}

export default MyAppsPage;
