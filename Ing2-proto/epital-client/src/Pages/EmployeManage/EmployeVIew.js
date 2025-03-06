import { useContext, useEffect } from "react";
import Epital from "../Epital";
import MainMenu from "../Home/Components/mainMenu";
import EmployeCard from "./components/EmployeCard";
import { useState } from "react";


export default function EmployeVIew(){

    const con = useContext(Epital);
    const [employeList, setEmployeList] = useState([]);
    useEffect(()=>{
        const FetchEmployes = async() => {
            const res = await fetch('http://172.31.249.204:8089/epital-api/employes');
            const resData = await res.json();
            setEmployeList(resData);
        };
        FetchEmployes();
     }, []);

    return(
        <>
        <div class="flex flex-row justify-between items-center bg-gray-900 min-h-screen"> 
            <div>{con.showMenu && <MainMenu/>}</div>

            <div><EmployeCard employe ={employeList}/></div>

            <div></div>
        </div>
        
        </>

    );
}