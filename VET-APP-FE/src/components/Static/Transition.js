import React from 'react'
import classes from './Transition.module.css'; 
import { Link } from 'react-router-dom';

export default function Transition() {
    return (
        <div className={classes.mySidenav}>
        <Link className={classes.user} to="/userMgt">User Mgmt</Link>
        <Link className={classes.request} to="/reqMgt">Request Mgmt</Link>
        <Link className={classes.animal} to="/animalMgt">Animal Mgmt</Link>    
        </div>   
    )
}
