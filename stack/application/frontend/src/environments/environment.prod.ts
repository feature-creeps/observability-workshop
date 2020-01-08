export const environment = {
  production: true,
  backend: {
    imageorchestrator: 'http://46.101.170.74/proxy/imageorchestrator',
    imageholder: 'http://46.101.170.74/proxy/imageholder',
    imagerotator: 'http://46.101.170.74/proxy/imagerotator',
    imagegrayscale: 'http://46.101.170.74/proxy/imagegrayscale',
    imageresize: 'http://46.101.170.74/proxy/imageresize',
    imageflip: 'http://46.101.170.74/proxy/imageflip',
    imagethumbnail: 'http://46.101.170.74/proxy/imagethumbnail'
  },
  tools: {
    kibana: 'http://46.101.170.74:5601',
    grafana: 'http://46.101.170.74:3000',
    prometheus: 'http://46.101.170.74:9090',
    zipkin: 'http://46.101.170.74:9411',
  }
};
