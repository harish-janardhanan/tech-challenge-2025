import {Navigate} from "react-router-dom";

 const PrivateRoute = ({children}: {children:JSX.Element}) => {
    const  authenticated = localStorage.getItem('authenticated') === 'true';
    return authenticated ?  {children} : <Navigate to="/signin" replace /> ;
}
export default PrivateRoute;