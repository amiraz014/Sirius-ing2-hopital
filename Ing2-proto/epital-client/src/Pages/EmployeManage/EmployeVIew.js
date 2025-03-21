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
            const res = await fetch('http://localhost:8089/epital-api/employes');
            const resData = await res.json();
            setEmployeList(resData);
        };
        FetchEmployes();
     }, []);
     console.table(employeList);
     
    return(
        <>
        <div class=" flex flex-row justify-between bg-gray-900 text-white">
            <div>{con.showMenu && <MainMenu/>}</div>
    <div class="min-h-screen flex justify-center items-center p-4">
        <div class="w-full max-w-6xl overflow-auto bg-gray-800 shadow-lg rounded-2xl p-6">
            <h1 class="text-3xl font-bold text-center mb-6">Liste des Employés</h1>
            <div class="flex flex-wrap gap-4 overflow-x-auto overflow-y-auto max-h-[80vh] p-2">
                <EmployeCard employes={employeList} />
            </div>
        </div>
    </div>
    <div></div>
</div>
        
        </>

    );
}