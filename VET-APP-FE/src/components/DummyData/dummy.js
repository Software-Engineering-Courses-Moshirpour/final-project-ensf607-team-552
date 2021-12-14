  export const ROLE_ADMIN="ROLE_ADMIN";
  export const ROLE_ANIMALCAREAT="ROLE_ANIMALCAREAT";
  export const ROLE_ANIMALHTTECH="ROLE_ANIMALHTTECH";
  export const ROLE_TEACHINGTECH="ROLE_TEACHINGTECH";
  export const ROLE_STUDENT="ROLE_STUDENT";


  export const APPROVED="APPROVED";
  export const DECLINED="DECLINED";
  export const PENDING="PENDING";
  export const PROCESSING="PROCESSING";
  export const PRESCRIBED="PRESCRIBED";
  export const TREATMENT="TREATMENT";
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
  },
  {
    id:'4',
    stsName:'Treatment'
  }
  ]

  const treatmentMethod1 =[
    {
      id:'1',
      stsName:'Physical exam'
  },
    {
      id:'2',
      stsName:'Blood work'
  },
  {
    id:'3',
    stsName:'Bordetella vaccine'
  },
  {
    id:'4',
    stsName:'Dental cleaning'
  },
  {
    id:'5',
    stsName:'Deworming'
  },
  {
    id:'6',
    stsName:'Rabies Vaccination'
  },
  {
    id:'7',
    stsName:'Chemo Treatment'
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
  export const treatmentMethod=treatmentMethod1;
  export const animalStatus=animalStatus1;

  export const roledata = roleData;

  export const statusData = statusData1;