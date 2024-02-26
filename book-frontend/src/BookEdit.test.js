import React from "react";
import { render, screen , fireEvent, act} from '@testing-library/react';

import App from "./App";

describe('test BookEdit', () => {
  beforeEach(() =>{
    fetchMock.doMock();
  }); 

  test('test Click Add Book', async () => {

        fetchMock.resetMocks();
        fetchMock.doMock();
        fetchMock.mockResponse(JSON.stringify({}));
    
    
        const { debug } = render(<App />);
    
        debug();
        const createBtn = screen.getByText("Add Book");
        expect(createBtn).toBeInTheDocument;
    
        fireEvent.click(createBtn);
    
        expect(await screen.findByText(/Save/)).toBeInTheDocument;
        expect(await screen.findByText(/Add Book/)).toBeInTheDocument;
    
        debug();
    
        //input form
    
        const inputTitle = screen.getByLabelText("Title");
        expect(inputTitle).toBeInTheDocument;
    
        const inputAuthor = screen.getByLabelText("Author");
        expect(inputAuthor).toBeInTheDocument;
    
        const inputPublicationYear = screen.getByLabelText("PublicationYear");
        expect(inputPublicationYear).toBeInTheDocument;
    
        const inputIsbn = screen.getByLabelText("Isbn");
        expect(inputIsbn).toBeInTheDocument;
    
        inputTitle.setAttribute("value", "Testing");
        inputAuthor.setAttribute("value", "Justin");
        inputPublicationYear.setAttribute("value", "2000");
        inputIsbn.setAttribute("value", "JCKY-YYYYY-OPEL-JJJJJ");
    
        const saveBtn = screen.getByText(/Save/);
        expect(saveBtn).toBeInTheDocument;
    
        debug();
    
        //mock a book save success, and GET /books will return one book
        fetchMock.resetMocks();
        fetchMock.doMock();
        fetchMock.mockResponse(JSON.stringify({
          "id": "d898a142-de44-466c-8c88-9ceb2c2429d3",
          "title": "Testing",
          "author": "Justin",
          "publicationYear": "2000",
          "isbn": "JCKY-YYYYY-OPEL-JJJJJ"
        }));
    
        act(() => {
          fireEvent.click(saveBtn);
        });
        
    
        debug();  
    
        // check if the list page have one book.
    
    /*     const createBtn2 = screen.getByText("Add Book");
        expect(createBtn2).toBeInTheDocument;
    
        // expect(await screen.findAllByText(/Delete/)).toHaveLength(1);
    
        expect(await screen.findByText(/Testing/)).toBeInTheDocument;
    
        debug(); */
      });
});


