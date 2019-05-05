### todo
* use an api proxy
* refresh component when on component and clicking on same route (button)
* orchestration: when persist, do return id/name and display
* improve Dockerfile to cache node modules
* improve Dockerfile to make use of multi stage
* remove old frontend components from imageholder, imageorchestrator, ..?
* add name field to orchestration form

### nice to have
* generic orchestrator frontend (plug and play image manipulations)
* display button/id towards particular image in random component
* add an album view
* model driven form in orchestrator

### may be
* set up nginx (if required)
* style text next to upload buttons
* redirect to display page after uploading a new image
* make name field mandatory and identical (do not allow name collision)
* check if cors configuration can be removed once we use the proxy conf successfully

### done
* support backend service configuration
* set up environments
* fix delete form
* show nice info if there are no images (display, delete, orchestrate)
* show info if there are now images (random)
* give feedback after uploading an image
* fix: propagating the name in upload does not work
* make delete all button more responsive and fix the "error response in the console"
* change tab icon
* force refresh when navigating to random. currently the last displayed image is still shown after deletion
* rebuild random component to use angular httpClient
* show nicer info in random component if there are now images (like other components) 
* all button font-size to 16px (similar to select)
* refresh animation on upload successful info (to indicate consecutive successful uploads)
* fix orchestrate form
* show transformed image after orchestration
