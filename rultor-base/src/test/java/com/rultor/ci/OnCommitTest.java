/**
 * Copyright (c) 2009-2013, rultor.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the rultor.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.rultor.ci;

import com.rultor.board.Billboard;
import com.rultor.scm.Branch;
import com.rultor.scm.Commit;
import com.rultor.shell.Batch;
import com.rultor.spi.Instance;
import com.rultor.tools.Time;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test case for {@link OnCommit}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
public final class OnCommitTest {

    /**
     * OnCommit can build when commit is available.
     * @throws Exception If some problem inside
     */
    @Test
    @SuppressWarnings("unchecked")
    public void buildsOnNewCommit() throws Exception {
        final Branch branch = Mockito.mock(Branch.class);
        Mockito.doReturn("master").when(branch).name();
        final Commit commit = new Commit.Simple("abce7f6", new Time(), "me");
        Mockito.doReturn(Arrays.asList(commit)).when(branch).log();
        final Batch batch = Mockito.mock(Batch.class);
        final Billboard board = Mockito.mock(Billboard.class);
        final Instance instance = new OnCommit(branch, batch, board);
        instance.pulse();
        Mockito.verify(batch).exec(
            Mockito.any(Map.class), Mockito.any(OutputStream.class)
        );
    }

}