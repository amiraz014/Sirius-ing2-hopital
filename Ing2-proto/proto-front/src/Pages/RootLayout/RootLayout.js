import {Outlet} from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import logo from './components/Epitalogo.svg';
export default function RootLayout(){

    return (
        <>

        <Header logo ={logo} logotitle='EPITAL' finame='Acceuil' siname='Gestion' tiname='Stockage' frname='Statistiques'/>
        <Outlet/>
        <Footer/> 
        </>
    );
}