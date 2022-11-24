import React, { useState, useEffect} from 'react'
import { Link } from 'react-router-dom'
import useAuth from '../hooks/useAuth'
import '../styles/ContentCreator/MyAppsPage/components/Application.css'

const BasicApplication = ({ name, icon }) => {

  const { auth } = useAuth();
  const [isCreator, setIsCreator] = useState(false)

    const checkRoleStatus = () => {
        auth?.roles?.map((role) => {
            if (role === "Creator") {
                setIsCreator(true) // TODO check if creator id matches application id
            }
        })
    }

    useEffect(() => {
        checkRoleStatus()
    }, [auth])

  return (
    <>
      {isCreator ? (
        <>
          <Link className='application' to={`/creator/app/${name}`}>
            <img src={icon} />
            <p>{name}</p>
          </Link>
        </>
      ) : (
        <>
          <Link className='application' to={`/app/${name}`}>
            <img src={icon} />
            <p>{name}</p>
          </Link>
        </>
      )}
    </>
    
    
  )
}

export default BasicApplication 