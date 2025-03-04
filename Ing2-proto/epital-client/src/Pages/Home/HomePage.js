import logo from './Epitalogo.svg';
import MainMenu from './Components/mainMenu';
import { useContext, useEffect, useState } from 'react';
import Epital from '../Epital';


export default function HomePage(){

  const [displayedText, setDisplayedText] = useState('');
  const [isDeleting, setIsDeleting] = useState(false);
  const [index, setIndex] = useState(0);
  const motto = "Votre santé est notre priorité";

  useEffect(() => {
    const timeout = setTimeout(() => {
      if (!isDeleting) {
        setDisplayedText(motto.slice(0, index + 1));
        setIndex(index + 1);
        if (index + 1 === motto.length) {
          setTimeout(() => setIsDeleting(true), 1000);
        }
      } else {
        setDisplayedText(motto.slice(0, index - 1));
        setIndex(index - 1);
        if (index === 0) {
          setIsDeleting(false);
        }
      }
    }, isDeleting ? 50 : 100);

    return () => clearTimeout(timeout);
  }, [index, isDeleting]);
  
    const con = useContext(Epital);
    return(
        <>
          <div class="flex flex-row space-x-30 items-start justify-start min-h-screen bg-gray-900">
              {con.showMenu && <MainMenu/>}      
                    
            <div class="flex flex-col items-center justify-center w-full h-full mt-48">
            <div class="animate-pulse mb-8">
           
           
            <img src={logo} alt="logo" className="w-60 h-60 flex items-center ml-15 animate-pulse"/>
              
              
              </div>
              <h1 class="text-4xl text-white font-bold italic  typing-text">{displayedText}</h1>
            </div>
            </div>  
        </>
    );
}
