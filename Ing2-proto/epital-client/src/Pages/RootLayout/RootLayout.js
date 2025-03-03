import {Outlet} from 'react-router-dom';
import Header from './components/Header';
import logo from './components/Epitalogo.svg';



export default function RootLayout(){
     
    return (
        <>
        <Header logo ={logo} logotitle='EPITAL'/>  
        <Outlet/>
    </>
    );
}