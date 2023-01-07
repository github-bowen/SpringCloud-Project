import axiosService from "@/request";



export async function request (url, method, params) {
  let response
  const options = { url, method }
  if (typeof params !== 'undefined') {
    if (method === 'get' || method === 'delete') {
      options.params = params
    } else {
      options.data = params
    }
    // console.log(options);
  }
  response = await axiosService(options)

  return response
}
