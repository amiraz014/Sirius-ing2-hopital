import {createContext} from 'react';

import {useState} from 'react';

const Epital = createContext({
    showMenu: false,
    setShowMenu: () => {},
    authToken: "",
    setAuthToken: ()=>{},
    username: "",
    setUsername: ()=>{},
 });

 export const EpitalProvider = (props) => {
     const [showMenu, setShowMenu] = useState(false);
     const [authToken, setAuthToken] = useState("");
     const [username, setUsername] = useState("");
        return (
            <Epital.Provider value={{
                 showMenu: showMenu,
                 setShowMenu: setShowMenu,
                 authToken: authToken,
                 setAuthToken: setAuthToken,
                 username: username,
                 setUsername: setUsername,
                  }}>
                {props.children}
            </Epital.Provider>
        );
 };
    export default Epital;
 