import { Button } from '@headlessui/react';
import { useNavigate } from 'react-router-dom';



export default function AbsenceInterfaceButton(){
        const navigate = useNavigate();
        const handleRedirectionButton = () => {
                navigate('/login');
        };
    return(
        <>
         <Button 
         className="inline-flex items-center gap-2 rounded-md bg-blue-400 py-1.5 px-3 text-sm/6 font-semibold text-white shadow-inner shadow-white/10 focus:outline-none data-[hover]:bg-gray-600 data-[open]:bg-gray-700 data-[focus]:outline-1 data-[focus]:outline-white"
            onClick={handleRedirectionButton}
         >
      Gestion des Absences
    </Button>
        </>
    );

}