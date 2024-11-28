import {createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './Pages/RootLayout/RootLayout';
import HomePage from './Pages/Home/HomePage';
import FormPage from './Pages/Form/FormPage';
import UserPage from './Pages/User/UserPage';
function App() {

  const router = createBrowserRouter([
    {path : '/',
      element : <RootLayout/>,
      children : [
        { path : '/' , element : <HomePage/>},
        {path : '/form', element : <FormPage/>},
        {path : '/list', element: <UserPage/>}
      ]
    }
  ]);
  return (
   <>
   <RouterProvider router = {router}/>
   </>
  );
}

export default App;
