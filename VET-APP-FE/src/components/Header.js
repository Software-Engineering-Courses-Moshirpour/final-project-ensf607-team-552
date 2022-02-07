import { useSelector, useDispatch } from 'react-redux';
import { NavLink } from 'react-router-dom';

import classes from './Header.module.css';
import { authActions } from '../store/auth';
import { Popconfirm, message } from 'antd';
import { useHistory } from "react-router-dom";
import { useState,useEffect } from 'react';

const Header = () => {
  let history = useHistory();
  const dispatch = useDispatch();
  const isAuth = useSelector((state) => state.auth.isAuthenticated);
  const [username, setusername] = useState('');


  useEffect(() => {
   setusername(localStorage.getItem("userName"))
  }, [localStorage.getItem("userName")])


  function confirm(e) {
    sessionStorage.removeItem("token");
    localStorage.clear();
    dispatch(authActions.logout());
    history.push('/');

  }
  
  function cancel(e) {
    message.error('Ohhh let us stay here');
  }

  return (
    <header className={classes.header}>
      <h1><NavLink to="/">Vet Application</NavLink></h1>
      {isAuth && (
        <nav>
          <ul>
            <li>
              current user is: {username}
            </li>
            <li>
            <Popconfirm
              title="Are you sure to log out?"
              onConfirm={confirm}
              onCancel={cancel}
              okText="Yes"
              cancelText="No"
            >
            <button>
              LogOut
            </button>
            </Popconfirm>
            </li>
          </ul>
        </nav>
      )}
    </header>
  );
};

export default Header;
