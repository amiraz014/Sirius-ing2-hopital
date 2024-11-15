import {Outlet} from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import logo from './components/Epitalogo.svg';
export default function RootLayout(){

    return (
        <>

        <Header logo ={logo} logotitle='EPITAL' finame='Home' siname='Add' tiname='Show'/>
        <Outlet/>
        <Footer/> 
        </>
    );
}