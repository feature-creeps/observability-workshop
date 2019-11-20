export const environment = {
  production: true,
  backend: {
    imageorchestrator: 'http://46.101.170.74:8080',
    imageholder: 'http://46.101.170.74:8081',
    imagerotator: 'http://46.101.170.74:8082',
    imagegrayscale: 'http://46.101.170.74:8083',
    imageresize: 'http://46.101.170.74:8084',
    imageflip: 'http://46.101.170.74:8085',
    imagethumbnail: 'http://46.101.170.74:8086'
  },
  tools: {
    kibana: 'http://46.101.170.74:5601',
    grafana: 'http://46.101.170.74:3000',
    prometheus: 'http://46.101.170.74:9090',
    zipkin: 'http://46.101.170.74:9411',
  }
};
