import React from 'react';
import "../../styles/Authentication&Authorisation/ErrorPage.css";
import error from "../../images/404.gif";
import {Link} from "react-router-dom";

function ErrorPage() {
  return (
      <div className="NotFound">
          <img src={error} alt={"404"}/>
          <p>Sorry, we couldn't find this page. But don't worry, you can find plenty of other things on our <Link className="NotFoundLink" to="/">home page</Link>.</p>
      </div>
  )
}

export default ErrorPage