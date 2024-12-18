import { UserProfile } from "libs/model";
import moment from "moment";

const isExpiredUser = (user?: UserProfile | null) => {
  if (!user) {
    return false;
  }

  return moment(Number(user.expiredAt)).isBefore(moment());
};

const AuthUtils = {
  isExpiredUser,
};

export default AuthUtils;
