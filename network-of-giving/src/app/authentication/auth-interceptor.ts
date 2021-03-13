import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
 
import { TokenStorageService } from './token-storage.service';
 
const TOKEN_HEADER_KEY = 'Authorization';
export const InterceptorSkipHeader = 'X-Skip-Interceptor';
 
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
 
    constructor(private token: TokenStorageService) { }
 
    intercept(req: HttpRequest<any>, next: HttpHandler) {
        if (req.headers.has(InterceptorSkipHeader)) {
            const headers = req.headers.delete(InterceptorSkipHeader);
            return next.handle(req.clone({ headers }));
          }
        let authReq = req;
        const token = this.token.getToken();
        if (token != null) {
            authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
        }

        console.log('Bearer ' + token);
        return next.handle(authReq);
    }
}
 
