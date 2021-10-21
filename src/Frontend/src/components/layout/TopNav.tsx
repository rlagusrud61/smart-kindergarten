import {Link} from "react-router-dom";
import "../../styles/_topnav.scss";

export const TopNav = () => {
    return (<nav>
        <Link to='/'>Home</Link>
        <Link to='/OtherPage'>Somewhere Else</Link>
    </nav>);
}
