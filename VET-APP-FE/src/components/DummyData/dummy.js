  export const ROLE_ADMIN="ROLE_ADMIN";
  export const ROLE_ANIMALCAREAT="ROLE_ANIMALCAREAT";
  export const ROLE_ANIMALHTTECH="ROLE_ANIMALHTTECH";
  export const ROLE_TEACHINGTECH="ROLE_TEACHINGTECH";
  export const ROLE_STUDENT="ROLE_STUDENT";


  export const APPROVED="APPROVED";
  export const DECLINED="DECLINED";
  export const PENDING="PENDING";


  const roleData =[
      {
          id:'1',
          roleName:'ROLE_ADMIN'
      },
      {
        id:'2',
        roleName:'ROLE_ANIMALCAREAT'
    },
    {
        id:'3',
        roleName:'ROLE_ANIMALHTTECH'
    },
    {
        id:'4',
        roleName:'ROLE_TEACHINGTECH'
    },
    {
        id:'5',
        roleName:'ROLE_STUDENT'
    },
  ]


  
  const animalStatus1 =[
    {
      id:'1',
      stsName:'Available'
  },
    {
      id:'2',
      stsName:'Injured'
  },
  {
    id:'3',
    stsName:'Sick'
  },
  {
    id:'4',
    stsName:'Unavailable'
  }
  ]


  const statusData1 =[
    {
      id:'1',
      stsName:'ACTIVE'
  },
    {
      id:'2',
      stsName:'INACTIVE'
  },
  {
    id:'3',
    stsName:'UNKNOWN'
  }
  ]

  export const animalStatus=animalStatus1;

  export const roledata = roleData;

  export const statusData = statusData1;