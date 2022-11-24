import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { useNavigate } from 'react-router-dom';
import axios from 'axios'
import useAuth from '../../hooks/useAuth';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCaretDown} from '@fortawesome/free-solid-svg-icons'
import { BsQuestionCircle } from 'react-icons/bs'

import ReviewList from '../Customer/ApplicationDetails/component/ReviewList';
import AdminReviewList from "../ContentCreator/ApplicationPage/components/AdminReviewList";
import Tutorial from '../../components/Tutorial';


const DetailedApplicationPage = () => {
    const { auth } = useAuth();

    const [ownReview, setOwnReview] = useState(null);
    const [updateList, setUpdateList] = useState(true);
    const username = auth?.decoded?.sub;

    const [isCreator, setIsCreator] = useState(false)
    const navigate =  useNavigate()

    useEffect(() => {
        getApplication()
    }, [updateList])

    useEffect(() => {
        checkRoleStatus()
    }, [auth])


    const params = useParams();
    const URL = `http://localhost:8080/application/${params.name}`

    const [application, setApplication] = useState({})
    const [version, setVersion] = useState("1.0")
    const [appLocation, setAppLocation] = useState('')
    const[arrayOfBytes,setArrayOfBytes] = useState('')

    const getApplication = () => {
        axios.get(URL)
        .then(res => {
            setApplication(res.data)
            setAppLocation(res.data.versions[0].appLocation)
            console.log(res.data.versions[0].appLocation)
        })
        .catch(err => {
            console.log(err)
        })
        
    }

    function base64ToArrayBuffer(base64) {
        let binaryString = window.atob(base64);
        let binaryLen = binaryString.length;
        let bytes = new Uint8Array(binaryLen);
        for (let i = 0; i < binaryLen; i++) {
            let ascii = binaryString.charCodeAt(i);
            bytes[i] = ascii;
        }
        return bytes;
    }

    const saveByteArray = (name, byte)  => {
        let blob = new Blob([byte], {type: "image/png"});
        let link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        let fileName = name;
        link.download = fileName;
        link.click();
    };

    const downloadApplication = async (e) => {
        e.preventDefault();
        try {
            console.log("METHOD STARTED");
            console.log(appLocation);
            console.log(application.name);
            const response = await axios.get(`http://localhost:8080/application/download/${appLocation}/${application.name}`)
                .then(
                    response => {
                        setArrayOfBytes(response.data);
                        let bytes = base64ToArrayBuffer(arrayOfBytes.arrayOfBytes)
                        saveByteArray(application.name, bytes);
                        console.log(arrayOfBytes);
                        console.log(arrayOfBytes.arrayOfBytes);
                    }
                )
            console.log("SUCCESSFUL");
        } catch (err){
            console.log("Something went wrong");
        }
    }


    useEffect(() => {
        const username = localStorage.getItem("username");
        if (username === application?.creator?.username){
            setIsCreator(true)
        } else {
            setIsCreator(false)
        }
    }, [application])

    
    const checkRoleStatus = () => {
        auth?.roles?.map((role) => {
            if (role === "Creator") {
                setIsCreator(true)
            }
        })
    }

    useEffect(() => {
        getReview();
    }, [application])

    const getReview = () => {
        axios.get(`http://localhost:8080/review/${username}/${application.name}`)
            .then(res => {
                setOwnReview(res.data);
            })
            .catch(err => {
                console.log(err.message);
                setOwnReview(null);
            });
    }

    const deleteApp = ()=>{
        const config = {
            headers: { Authorization: 'Bearer ' + auth?.accessToken}
        }

        axios.put(`http://localhost:8080/application/${params.name}`, null, config)
            .then(function (){})
            .catch(err => {
                console.log(err)}
            )
            navigate('/creator/my-apps')
    }

    // tutorial
    const [showTutorial, setShowTutorial] = useState(false) 
    const closeTutorial = () => {
        setShowTutorial(false)
    }
    
    const tutorialContent = {
        'title': 'Application Info',
        'header': 'Manage your application',
        'body': 'This is the place to manage your application. If you want to update the screenshots or description, you can press the edit button. If you want to upload a new version of your application, click the Add Major Version or Add Minor Version, depending on what version you want to add. To discontinue click the Discontinue button, and to reactivate a discontinued app click the active button.'
    }

    const tutorialContentCustomer = {
        'title': 'Application Info',
        'header': 'Download this application',
        'body': 'When logged in to your customer account you can download this application. Just click the download button and the download should begin.'
    }


  return (
    <div className='app'>
        <div className='app-controls'>
            <img className='icon' alt='application logo' src={application.icon} />
            <h1>{application.name}</h1>
            { isCreator ? (
                <>
                    <Tutorial visible={showTutorial} closeTutorial={closeTutorial} content={tutorialContent} />
                    <button onClick={() => setShowTutorial(true)} className='btn-open-tutorial'><BsQuestionCircle /></button>
                    <Link className='edit-button' to={`/creator/update/${application.name}`}>Edit</Link>
                    { application.discontinued ? (
                        <>
                            <button className='delete-button' onClick={deleteApp}>Activate</button>
                        </>
                    ) : (
                        <>
                            <button className='delete-button' onClick={deleteApp}>Discontinue</button>
                        </>
                    )}

                </>
            ) : (
                <>
                    <Tutorial visible={showTutorial} closeTutorial={closeTutorial} content={tutorialContentCustomer} />
                    <button onClick={() => setShowTutorial(true)} className='btn-open-tutorial'><BsQuestionCircle /></button>
                    {auth?.accessToken ? <>
                    <a href={appLocation} className='edit-button' target='_blank'>Download</a>
                     </> : null}
                    
                </>
            )}
        </div>
        <hr />
        <div className='app-version'>
            <h2>Version</h2>
            <div className='version-content'>
                <dropdown className='version-selector'>
                    <select value={version} onChange={(e) => setVersion(e.target.value)}>
                        {application?.versions?.map((version) => (
                            <option>{parseFloat(version.number).toFixed(1)}</option>
                        ))}
                    </select>
                    <FontAwesomeIcon className='dropdown-icon' icon={faCaretDown} />
                </dropdown>
                { isCreator ? (
                    <>
                        <Link className='version-button' to={`/creator/version/${application.name}/minor`}>Add minor version</Link>
                        <Link className='version-button' to={`/creator/version/${application.name}/major`}>Add major version</Link>
                    </>
                ) : (null)}
            </div>
        </div>
        <hr />
        <div className='menu-wrapper'>
            <ul className='menu'>
                {application?.screenshots?.map((image) => (
                    <li className='item' key={image}><img src={image} /></li>
                ))}
            </ul>
        </div>
        <hr />
        <div className='ratings-header'>
            <h2>Ratings & Reviews</h2>
            <button className='see-all-button'>See All</button>
        </div>
        <div className='overall-rating'>
            <p className='rating-number'>{application.avgRating?.toFixed(1)}</p>
            <p>out of 5</p>
        </div>
        {isCreator?
            <AdminReviewList reviews = {application?.reviews} setUpdate={setUpdateList}/>:
            <ReviewList ownReview={ownReview?ownReview:null} reviews = {application?.reviews} app = {application?.name} setUpdate={setUpdateList} creator={application?.creator?.username}/>
        }

        <hr />
        <div className='app-description'>
            <h2>Description</h2>
            <p>{application.description}</p>
        </div>
    </div>
  )
}

export default DetailedApplicationPage