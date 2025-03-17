import {  useEffect } from "react";

import EmployeCard from "./components/EmployeCard";
import { useState } from "react";


export default function EmployeVIew(){

    
    const [employeList, setEmployeList] = useState([]);
    useEffect(()=>{
        const FetchEmployes = async() => {
            const res = await fetch('http://172.31.249.204:8089/epital-api/employes');
            const resData = await res.json();
            
            setEmployeList(resData);
        };
        FetchEmployes();
        
     }, []);
      
     console.table(employeList)
    return(
        <>
        <div class="flex flex-row justify-between items-center bg-gray-900 min-h-screen"> 
            

            <EmployeCard employes ={employeList} />

            
        </div>
        
        </>

    );
}