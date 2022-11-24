import React, {useEffect, useState} from 'react';
import BasicApplication from "../../components/BasicApplication";
import '../../styles/HomePage.css'
import loading from "../../images/loading.gif";
import axios from "axios";

function HomePage () {
  const [applications, setApplications] = useState([]);

  const getApplications = () => {
    axios.get(`http://localhost:8080/application?sort=popularity`)
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
          <div className='home'>
          <h1 className='title'>Featured Applications</h1>
              <hr/>
          {applications.length !== 0 ? (
              <div className='my-apps-list'>
                { applications?.slice(0, 10).map((app) => (
                    <BasicApplication key={app.name} name={app.name} icon={app.icon} />
                ))}
              </div>
          ) : (
              <img className={"loading-apps"} src={loading}/>
          )}
        </div>
      </>
  );
};

export default HomePage;