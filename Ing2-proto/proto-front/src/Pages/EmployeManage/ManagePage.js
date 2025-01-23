import AbsenceInterfaceButton from "./components/AbsenceInterfaceButton";
import EmployeInterfaceButton from "./components/EmployeInterfaceButton";

export default function ManagePage () {
    return (
        <div  className="flex flex-row space-x-6 items-center justify-center min-h-screen bg-gray-900 text-white">
            <EmployeInterfaceButton />
            <AbsenceInterfaceButton/>
        </div>
    );
}