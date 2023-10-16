import {ApplicationRef, ComponentFactoryResolver, EmbeddedViewRef, Injectable, Injector} from '@angular/core';
import {PreviewComponent} from "../components/preview/preview.component";


@Injectable()
export class PreviewService {

  constructor(private componentFactoryResolver: ComponentFactoryResolver,
              private appRef: ApplicationRef,
              private injector: Injector) {
  }

  appendComponentToBody(component: any, id: string, name: string) {
    // Create a component reference from the component
    const componentRef = this.componentFactoryResolver
      .resolveComponentFactory(component)
      .create(this.injector);

    let previewComp = (<PreviewComponent>componentRef.instance);
    previewComp.showImage(id, name)

    // Attach component to the appRef so that it's inside the ng component tree
    this.appRef.attachView(componentRef.hostView);

    // Get DOM element from component
    const domElem = (componentRef.hostView as EmbeddedViewRef<any>)
      .rootNodes[0] as HTMLElement;

    // Append DOM element to the body
    document.querySelectorAll("#album")[0].appendChild(domElem);

    // Wait some time and remove it from the component tree and from the DOM
    //setTimeout(() => {
    //  this.appRef.detachView(componentRef.hostView);
    //  componentRef.destroy();
    //}, 3000);
  }
}
