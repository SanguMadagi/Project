const BASE_URL = "http://localhost:8080/api/products";

// 👇 Get JWT token (after login / OAuth)
const getToken = () => localStorage.getItem("token");

export const getAllProducts = async () => {
  const res = await fetch(BASE_URL, {
    headers: {
      "Authorization": `Bearer ${getToken()}`,
    },
  });
  return res.json();
};

export const searchProducts = async (query) => {
  const res = await fetch(`${BASE_URL}/search?name=${query}`, {
    headers: {
      "Authorization": `Bearer ${getToken()}`,
    },
  });
  return res.json();
};
