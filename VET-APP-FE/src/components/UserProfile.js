import classes from './UserProfile.module.css';
import Transition from './Static/Transition';
import { Fragment } from 'react';
const UserProfile = () => {
  return (
    <Fragment>
      <Transition></Transition>
      <main className={classes.profile}>
        <h2>My User Profile</h2>
      </main>
    </Fragment>
  );
};

export default UserProfile;
