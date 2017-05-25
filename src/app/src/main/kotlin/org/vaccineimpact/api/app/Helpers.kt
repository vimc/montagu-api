package org.vaccineimpact.api.app

import spark.Request
import spark.Response
import javax.servlet.http.HttpServletResponse

// The idea is that as this file grows, I'll group helpers and split them off into files/classes with more
// specific aims.

fun addTrailingSlashes(req: Request, res: Response)
{
    if (!req.pathInfo().endsWith("/"))
    {
        var path = req.pathInfo() + "/"
        if (req.queryString() != null)
        {
            path += "?" + req.queryString()
        }
        res.redirect(path)
    }
}

fun addDefaultResponseHeaders(res: Response, contentType: String = "${ContentTypes.json}; charset=utf-8")
    = addDefaultResponseHeaders(res.raw(), contentType = contentType)
fun addDefaultResponseHeaders(res: HttpServletResponse, contentType: String = "${ContentTypes.json}; charset=utf-8")
{
    res.contentType = contentType
    res.addHeader("Content-Encoding", "gzip")
    res.addHeader("Access-Control-Allow-Origin", "*")
}