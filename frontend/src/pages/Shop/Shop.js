import React, { useState, useEffect } from "react";
import Breadcrumbs from "../../components/pageProps/Breadcrumbs";
import Pagination from "../../components/pageProps/shopPage/Pagination";
import ProductBanner from "../../components/pageProps/shopPage/ProductBanner";
import ShopSideNav from "../../components/pageProps/shopPage/ShopSideNav";
import RemoteServices, { PRODUCTS_URL } from "../../services/RemoveServices";

const Shop = () => {
  const [itemsPerPage, setItemsPerPage] = useState(4);
  const [products, setProducts] = useState([]); // Estado para armazenar os produtos
  const [loading, setLoading] = useState(true); // Estado para controlar o carregamento
  const [error, setError] = useState(null);     // Estado para capturar erros

  // Função para buscar produtos do backend
  const fetchProducts = async () => {
    try {
      const response = await RemoteServices.httpProducts.get(PRODUCTS_URL);  // Ajuste a URL conforme sua API
      setProducts(response.data);
      console.log(response.data);
      setLoading(false);
    } catch (err) {
      setError("Erro ao buscar os produtos.");
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();  // Chama a função quando o componente monta
  }, []);

  const itemsPerPageFromBanner = (itemsPerPage) => {
    setItemsPerPage(itemsPerPage);
  };

  // Renderização condicional com carregamento e erro
  if (loading) return <div>Carregando...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="max-w-container mx-auto px-4">
      <Breadcrumbs title="Products" />
      {/* ================= Products Start here =================== */}
      <div className="w-full h-full flex pb-20 gap-10">
        <div className="w-[20%] lgl:w-[25%] hidden mdl:inline-flex h-full">
          <ShopSideNav />
        </div>
        <div className="w-full mdl:w-[80%] lgl:w-[75%] h-full flex flex-col gap-10">
          <ProductBanner itemsPerPageFromBanner={itemsPerPageFromBanner} products={products} />
          
          {/* Passa os produtos recebidos para o componente Pagination */}
          <Pagination itemsPerPage={itemsPerPage} products={products} />
        </div>
      </div>
      {/* ================= Products End here ===================== */}
    </div>
  );
};

export default Shop;