import { useEffect, useState } from "react";
import Table from "./components/Table";

export default function UserPage() {
    const [userList, setUserList] = useState([]);

    useEffect(()=>{
        const FetchUsers = async() => {
            const res = await fetch('http://172.31.253.194:8089/epital-api/users');
            const resData = await res.json();
            setUserList(resData);
        };
        FetchUsers();

    },[]);


    return (
        <>
         <div className="flex flex-col items-center justify-center min-h-screen bg-gray-900 text-white">
           <Table
           user={userList}
           />
           </div>
        </>
    );
}