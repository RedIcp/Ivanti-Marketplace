import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCaretDown, faMagnifyingGlass} from '@fortawesome/free-solid-svg-icons'
import '../../styles/ContentCreator/MyAppsPage/MyAppsPage.css';
import loading from "../../images/loading.gif";
import { useStateWithCallbackLazy } from 'use-state-with-callback';
import BasicApplication from '../../components/BasicApplication';

const AllApplicationsPage = () => {
    const [applications, setApplications] = useState([]);

    const [name, setName] = useState('');

    const [sort, setSort] = useState('');

    const getApplications = async () => {
        await axios.get(`http://localhost:8080/application?name=${name}&sort=${sort}`)
            .then(res => {
                setApplications(res.data.applications);
                console.log(res.data.applications);
            })
            .catch(err => {
                console.log(err);
            })
    }

    useEffect(() => {
        getApplications();
    }, []);

    return (
        <>
            <div className='my-apps'>
                <div className='my-apps-controls'>
                    <h1 className='title'>Apps</h1>
                    <input className='search-field' type='text' placeholder='Search' onChange={(e) => setName(e.target.value)}/>
                    <div className='dropdown'>
                        <select onChange={(e) => setSort(e.target.value)}>
                            <option value="nameAsc">Name &uarr;</option>
                            <option value="nameDesc">Name &darr;</option>
                            <option value="popularity">Popularity</option>
                        </select>
                        <FontAwesomeIcon className="dropdown-icon" icon={faCaretDown}/>
                    </div>
                    <button className='search-button' onClick={getApplications}><FontAwesomeIcon className='button-icon' icon={faMagnifyingGlass}/></button>
                </div>
                <hr/>
                {applications.length !== 0 ? (
                    <div className='my-apps-list'>
                        { applications?.map((app) => (
                            <BasicApplication key={app.name} name={app.name} icon={app.icon} />
                        ))}
                    </div>
                ) : (
                    <img className={"loading-apps"} src={loading}/>
                )}
            </div>
        </>
    )
}

export default AllApplicationsPage