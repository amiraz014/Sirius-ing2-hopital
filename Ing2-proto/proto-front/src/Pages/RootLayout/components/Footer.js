


export default function Footer(){
    return(
        <>
        <div className="bg-blue-400 flex flex-row content-center space-x-40 h-24">
            <div className="flex flex-col space-y-4 ">
                <ul>
                    <li>Contact us</li>
                        <li>LinkedIn :</li>
                        <li>Facebook :</li>
                        <li>Twitter :</li>
                    
                </ul>
            </div>
            <div className="flex flex-col space-y-4 ">
                <ul>
                <li>About us</li>
                </ul>
            </div>
            <div>
                <p className="italic"> @Copyright AmirHamza</p>
            </div>

        </div>
        </>
    );
}