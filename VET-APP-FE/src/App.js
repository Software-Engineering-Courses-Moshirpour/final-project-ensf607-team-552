import { Fragment } from 'react';
import { useSelector } from 'react-redux';
import { Route } from 'react-router-dom';


import Header from './components/Header';
import Auth from './components/Auth/Auth';
import ResetPwd from './components/Auth/ResetPwd';
import UserMgt from './components/Mgt/UserMgt';
import UserEdit from './components/Mgt/UserEdit';
import UserAdd from './components/Mgt/UserAdd';

import AnimalMgt from './components/Mgt/AnimalMgt';
import AnimalPrescribe from './components/Mgt/AnimalPrescribe';

import ReqMgt from './components/Mgt/Req/ReqMgt';
import AnimalProfile from './components/Profile/AnimalProfile';


import TreatmentMgt from './components/Mgt/TreatmentMgt';
import PrescriptionMgt from './components/Mgt/PrescriptionMgt';


import { authActions } from './store/auth';
import { useDispatch } from 'react-redux';
import AnimalEdit from './components/Mgt/AnimalEdit';
import { ROLE_ADMIN,ROLE_ANIMALHTTECH } from './components/DummyData/dummy';



function App() {
  const dispatch = useDispatch();
  if(sessionStorage.getItem("token")){
    dispatch(authActions.login());
  }
  const isAuth = useSelector(state => state.auth.isAuthenticated);

  return (
    <Fragment>
      <header>
        <Header />
      </header>
      <main>
      <Route path="/" exact>
        {!isAuth && <Auth />}
        {localStorage.getItem("role")==ROLE_ADMIN && isAuth && <UserMgt />}
        {localStorage.getItem("role")!=ROLE_ADMIN &&isAuth && <AnimalMgt />}
      </Route>
      <Route path="/pwdReset">
      {!isAuth &&  <ResetPwd />} 
      </Route>
      <Route path="/userMgt" exact>
        {!isAuth && <Auth />}
        {localStorage.getItem("role")==ROLE_ADMIN && isAuth && <UserMgt />}
      </Route>
      <Route path='/userMgt/:id/edit'>
       {isAuth && <UserEdit/>}
      </Route>
      <Route path='/userMgt/addUser'>
      {!isAuth && <Auth />}
       {isAuth && <UserAdd/>}
      </Route>
      <Route path="/animalMgt" exact>
        <AnimalMgt />
      </Route>
      <Route path='/animalMgt/:id/edit'>
       {isAuth && <AnimalEdit/>}
      </Route>



      {/* request mgmt */}
      <Route path="/reqMgt" exact>
        {!isAuth && <Auth />}
        {isAuth && <ReqMgt />}
      </Route>
      <Route path="/animalProfile/:id/view" exact>
        {!isAuth && <Auth />}
        {isAuth && <AnimalProfile />}
      </Route>
      <Route path="/treatmentMgt" exact>
        {!isAuth && <Auth />}
        {isAuth && <TreatmentMgt />}
      </Route>
      <Route path="/treatmentMgt/:id/edit" exact>
       {isAuth && <AnimalPrescribe/>}
      </Route>
      <Route path="/prescriptionMgt" exact>
        {!isAuth && <Auth />}
        {isAuth && <PrescriptionMgt />}
      </Route>
      </main>
    </Fragment>
  );
}

export default App;
