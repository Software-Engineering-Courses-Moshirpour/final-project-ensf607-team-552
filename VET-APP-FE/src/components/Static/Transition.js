import React from 'react'
import classes from './Transition.module.css'; 
import { Link } from 'react-router-dom';
import { ROLE_ADMIN, ROLE_STUDENT } from '../DummyData/dummy';

export default function Transition() {
    return (
        <div className={classes.mySidenav}>
        {localStorage.getItem("role")==ROLE_ADMIN && <Link className={classes.user} to="/userMgt">User Mgmt</Link>}
        {localStorage.getItem("role")!=ROLE_STUDENT &&  <Link className={classes.request} to="/reqMgt">Request Mgmt</Link>}
        <Link className={classes.animal} to="/animalMgt">Animal Mgmt</Link>    
        <Link className={classes.treatment} to="/treatmentMgt">Treatment Mgmt</Link>   
        </div>   
    )
}
