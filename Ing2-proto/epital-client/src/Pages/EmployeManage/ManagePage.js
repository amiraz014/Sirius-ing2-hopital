import { useContext } from "react";
import AbsenceInterfaceButton from "./components/AbsenceInterfaceButton";
import EmployeInterfaceButton from "./components/EmployeInterfaceButton";
import Epital from "../Epital";
import MainMenu from "../../Home/Components/mainMenu";

export default function ManagePage () {
    const con = useContext(Epital);
    return (
        <div  className="flex flex-row space-x-6 justify-between min-h-screen bg-gray-900 text-white">
           <div>{con.showMenu && <MainMenu/>}</div>
            <div class="flex flex-row space-x-6 items-center justify-center">
            <EmployeInterfaceButton/>
            <AbsenceInterfaceButton/>
            </div>
            <div></div>
        </div>
    );
}