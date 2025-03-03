import {Outlet} from 'react-router-dom';
import Header from './components/Header';
import { useState } from 'react';
import logo from './components/Epitalogo.svg';
import { Transition } from '@headlessui/react';
import MainMenu from './components/mainMenu';


export default function RootLayout(){
     
    return (
        <>
        <Header logo ={logo} logotitle='EPITAL'/>
        <Outlet/>
    </>
    );
}