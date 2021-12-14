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
      stsName:'PhysicalExam'
  },
    {
      id:'2',
      stsName:'BloodWork'
  },
  {
    id:'3',
    stsName:'BordetellaVaccine'
  },
  {
    id:'4',
    stsName:'DentalCleaning'
  },
  {
    id:'5',
    stsName:'Deworming'
  },
  {
    id:'6',
    stsName:'RabiesVaccination'
  },
  {
    id:'7',
    stsName:'ChemoTreatment'
  }
  ]

  const dailyStatusData1 =[
    {
      id:'1',
      stsName:'UnderObservation'
  },
    {
      id:'2',
      stsName:'Great'
  },
  {
    id:'3',
    stsName:'Alert'
  },
  {
    id:'4',
    stsName:'UnderTreatment'
  },
  {
    id:'5',
    stsName:'Recovering'
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
  export const dailyStatusData = dailyStatusData1;