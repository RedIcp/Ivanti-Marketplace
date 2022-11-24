import React, {useEffect, useState} from "react";
import axios from "axios";
import "../../../styles/ContentCreator/AnalyticsPage.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCaretDown} from "@fortawesome/free-solid-svg-icons";
import AnalyticsPerMonth from "./component/AnalyticsPerMonth";
import AnalyticsPerVersion from "./component/AnalyticsPerVersion";
import useAuth from "../../../hooks/useAuth";

function AnalyticsPage() {
    const [appsForMonth, setAppsForMonth] = useState([]);
    const [appsForVersion, setAppsForVersion] = useState([]);
    const [isMonth, setIsMonth] = useState(true);
    const [year, setYear] = useState(0);

    const {auth} = useAuth()
    const username = auth?.decoded?.sub

    useEffect(() => {
        getAllApplicationsAnalyticsForMonth();
    }, [isMonth, year]);

    useEffect(() => {
        getAllApplicationsAnalyticsForVersion();
    }, [isMonth]);

    const getAllApplicationsAnalyticsForMonth = () => {
        axios.get(`http://localhost:8080/user/` + username + `/statistics?year=`+year)
            .then(res => {
                setAppsForMonth(res.data);
            })
            .catch(err => {
                console.log(err.message);
            });
    }
    const getAllApplicationsAnalyticsForVersion = () => {
        axios.get(`http://localhost:8080/user/` + username + `/statistics/version`)
            .then(res => {
                setAppsForVersion(res.data);
            })
            .catch(err => {
                console.log(err.message);
            });
    }

    return (
        <div className={"analytics"}>
            <div className={'header'}>
                <h1 className={"title"}>Analytics</h1>
                <label>
                    <input
                        type={'radio'}
                        checked={isMonth}
                        onClick={(e) => setIsMonth(e)}
                    />
                    Per month
                </label>
                <label>
                    <input
                        type="radio"
                        checked={!isMonth}
                        onClick={(e) => setIsMonth(!e)}
                    />
                    Per version
                </label>
                <div className={"year-dropdown"}>
                    <select onInput={(e)=>{setYear(e.target.value)}}>
                        <option value={2022}>2022</option>
                        <option value={2021}>2021</option>
                    </select>
                    <FontAwesomeIcon className="dropdown-icon" icon={faCaretDown}/>
                </div>
            </div>
            <hr className={'analytics-line'}/>

            {isMonth ?
                <AnalyticsPerMonth applications={appsForMonth}/> :
                <AnalyticsPerVersion applications={appsForVersion}/>
            }
        </div>
    );
}

export default AnalyticsPage;