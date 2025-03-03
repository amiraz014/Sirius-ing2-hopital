import {createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './Pages/RootLayout/RootLayout';
import HomePage from './Pages/Home/HomePage';
import UserPage from './Pages/User/UserPage';
import ManagePage from './Pages/EmployeManage/ManagePage';
import GardFormular from './Pages/EmployeManage/components/GardFormular';
import StockSimulation from "./Pages/Stock/StockSimulation";
import Header from './Pages/RootLayout/components/Header';
import logo from './Pages/RootLayout/components/Epitalogo.svg';
function App() {

  const router = createBrowserRouter([
    {path : '/',
      element : <RootLayout/>,
      children : [
        { path : '/' , element : <HomePage/>},
        {path : '/Manage', element : <ManagePage/>},
        {path : '/list', element: <UserPage/>},
        {path : '/form', element : <GardFormular/>},
        {path : '/stock', element : <StockSimulation/>}
  ]}
  ]);
  return (
   <>
   
   <RouterProvider router = {router}/>
   </>
  );
}

export default App;
