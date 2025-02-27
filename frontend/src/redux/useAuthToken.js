import { useSelector } from 'react-redux';

const useAuthToken = () => {
  const token = useSelector((state) => state.orebi.token);
  return token;
};

export default useAuthToken;